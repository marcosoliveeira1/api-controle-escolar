import { InputType, Field, Int } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsInt, Min } from 'class-validator';
import { Gender } from './enums/gender.enum';
import { StudentLevel } from './enums/level.enum';

@InputType()
export class CreateStudentInput {
  @Field()
  @IsNotEmpty()
  @IsString()
  firstName: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  lastName: string;

  @Field(() => Gender)
  @IsNotEmpty()
  gender: Gender;

  @Field(() => Int)
  @IsInt()
  @Min(0)
  age: number;

  @Field(() => StudentLevel)
  @IsNotEmpty()
  level: StudentLevel;
  
  @Field()
  @IsNotEmpty()
  @IsString()
  guardianName: string;

  @Field(() => Int)
  @IsInt()
  schoolId: number;
}