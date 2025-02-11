import { Field, Int, ObjectType } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsInt, Min } from 'class-validator';
import { StudentLevel } from './enums/level.enum';
import { Gender } from './enums/gender.enum';

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
  gender: Gender;

  @Field(() => Int)
  @IsInt()
  @Min(0)
  age: number;

  @Field()
  @IsNotEmpty()
  @IsString()
  level: StudentLevel;

  @Field()
  @IsNotEmpty()
  @IsString()
  guardianName: string;

  @Field(() => Int)
  @IsInt()
  schoolId: number;
}