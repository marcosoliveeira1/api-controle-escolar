import { ObjectType, Field, Int } from '@nestjs/graphql';

@ObjectType()
export abstract class Page {
  @Field(() => Int)
  totalElements: number;

  @Field(() => Int)
  totalPages: number;

  @Field(() => Int)
  size: number;

  @Field(() => Int)
  number: number;
}