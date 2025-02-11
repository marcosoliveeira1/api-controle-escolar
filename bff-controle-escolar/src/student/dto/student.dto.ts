import { Field, Int, ObjectType } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsInt, Min } from 'class-validator';

@ObjectType()
export class StudentDTO {
  @Field(() => Int, { nullable: true })
  id?: number;

  @Field()
  @IsNotEmpty()
  @IsString()
  firstName: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  lastName: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  gender: string;

  @Field(() => Int)
  @IsInt()
  @Min(0)
  age: number;

  @Field()
  @IsNotEmpty()
  @IsString()
  level: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  guardianName: string;

  @Field(() => Int)
  @IsInt()
  schoolId: number;
}