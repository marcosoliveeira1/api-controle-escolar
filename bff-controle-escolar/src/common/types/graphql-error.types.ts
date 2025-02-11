import { Field, ObjectType } from '@nestjs/graphql';

@ObjectType()
export class GraphQLErrorExtension {
  @Field()
  code: string;

  @Field(() => [String], { nullable: true })
  errors?: string[];

  @Field({ nullable: true })
  statusCode?: number;
}

@ObjectType()
export class GraphQLErrorResponse {
  @Field()
  message: string;

  @Field(() => GraphQLErrorExtension)
  extensions: GraphQLErrorExtension;

  @Field(() => [String], { nullable: true })
  path?: string[];
}