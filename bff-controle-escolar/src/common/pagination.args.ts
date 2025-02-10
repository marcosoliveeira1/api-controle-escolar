import { ArgsType, Field, Int } from '@nestjs/graphql';
import { IsOptional, Min, IsString } from 'class-validator';

@ArgsType()
export class PaginationArgs {
    @Field(() => Int, { defaultValue: 0 })
    @IsOptional()
    @Min(0)
    page?: number;

    @Field(() => Int, { defaultValue: 10 })
    @IsOptional()
    @Min(1)
    size?: number;

    @Field({ nullable: true, defaultValue: 'id' })
    @IsOptional()
    @IsString()
    sortBy?: string;
}