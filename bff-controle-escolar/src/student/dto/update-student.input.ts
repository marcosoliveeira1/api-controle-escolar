import { InputType, Field, Int } from '@nestjs/graphql';
import { IsOptional, IsString, IsInt, Min } from 'class-validator';
import { Gender } from './enums/gender.enum';
import { StudentLevel } from './enums/level.enum';

@InputType()
export class UpdateStudentInput {
  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  firstName?: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  lastName?: string;

  @Field(() => Gender, { nullable: true })
  @IsOptional()
  gender?: Gender;

  @Field(() => Int, { nullable: true })
  @IsOptional()
  @IsInt()
  @Min(0)
  age?: number;

  @Field(() => StudentLevel, { nullable: true })
  @IsOptional()
  level?: StudentLevel;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  guardianName?: string;

  @Field(() => Int, { nullable: true })
  @IsOptional()
  @IsInt()
  schoolId?: number;
}