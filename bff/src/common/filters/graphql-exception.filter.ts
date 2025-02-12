import {
    Catch,
    ArgumentsHost,
    HttpException,
    HttpStatus,
    Logger,
} from '@nestjs/common';
import { GqlExceptionFilter, GqlArgumentsHost } from '@nestjs/graphql';
import { GraphQLError } from 'graphql';
import { AxiosError } from 'axios';

@Catch()
export class GlobalGraphQLExceptionFilter implements GqlExceptionFilter {
    private readonly logger = new Logger(GlobalGraphQLExceptionFilter.name);

    private formatError(
        code: string,
        message: string,
        path?: string[],
        extensions?: Record<string, any>
    ): GraphQLError {
        return new GraphQLError(message, {
            extensions: {
                code,
                ...extensions,
            },
            path,
        });
    }

    catch(exception: unknown, host: ArgumentsHost): GraphQLError {
        const gqlHost = GqlArgumentsHost.create(host);
        const path = gqlHost.getInfo()?.path?.typename
            ? [gqlHost.getInfo().path.typename]
            : undefined;

        if (exception instanceof AxiosError) {
            const status = exception.response?.status || HttpStatus.INTERNAL_SERVER_ERROR;
            const message = exception.response?.data?.message || 'Um erro ocorreu, tente novamente mais tarde';
            const code = this.getErrorCode(status) || 'INTERNAL_SERVER_ERROR';

            this.logger.error(
                `AxiosError ${status}: ${message}`,
                {
                    error: exception.message,
                    stack: exception.stack,
                    response: exception.response?.data,
                }
            );

            return this.formatError(
                code,
                message,
                path,
                {
                    errors: exception.response?.data?.errors || [],
                    originalError: {
                        status,
                        message: exception.message,
                    },
                }
            );
        }

        if (exception instanceof HttpException) {
            const status = exception.getStatus();
            const response = exception.getResponse();
            const message = typeof response === 'string'
                ? response
                : response['message'] || 'An error occurred';
            const code = this.getErrorCode(status) || 'INTERNAL_SERVER_ERROR';

            this.logger.error(
                `HttpException ${status}: ${message}`,
                {
                    error: exception.message,
                    stack: exception.stack,
                }
            );

            return this.formatError(
                code,
                message,
                path,
                {
                    errors: response['errors'] || [],
                    statusCode: status,
                }
            );
        }

        this.logger.error(
            'Unhandled error',
            {
                error: exception instanceof Error ? exception.message : 'Unknown error',
                stack: exception instanceof Error ? exception.stack : undefined,
            }
        );

        return this.formatError(
            'INTERNAL_SERVER_ERROR',
            'Internal server error',
            path,
            {
                errors: [],
            }
        );
    }

    private getErrorCode(status: number): string {
        const statusCodes = {
            [HttpStatus.BAD_REQUEST]: 'BAD_REQUEST',
            [HttpStatus.UNAUTHORIZED]: 'UNAUTHORIZED',
            [HttpStatus.FORBIDDEN]: 'FORBIDDEN',
            [HttpStatus.NOT_FOUND]: 'NOT_FOUND',
            [HttpStatus.CONFLICT]: 'CONFLICT',
            [HttpStatus.UNPROCESSABLE_ENTITY]: 'UNPROCESSABLE_ENTITY',
            [HttpStatus.INTERNAL_SERVER_ERROR]: 'INTERNAL_SERVER_ERROR',
        };

        return statusCodes[status] || 'INTERNAL_SERVER_ERROR';
    }
}