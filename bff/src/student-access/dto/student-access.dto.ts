import { Field, Int, ObjectType } from '@nestjs/graphql';

@ObjectType()
export class StudentAccessDTO {
  @Field(() => Int)
  id?: number;

  @Field(() => Int)
  studentId: number;

  @Field({ nullable: true })
  entryTime?: string;

  @Field({ nullable: true })
  exitTime?: string;

  @Field({ nullable: true })
  status?: string;
}