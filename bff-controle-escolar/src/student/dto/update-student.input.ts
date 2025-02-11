import { InputType, Field, Int } from '@nestjs/graphql';
import { IsOptional, IsString, IsInt, Min } from 'class-validator';

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

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  gender?: string;

  @Field(() => Int, { nullable: true })
  @IsOptional()
  @IsInt()
  @Min(0)
  age?: number;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  level?: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  guardianName?: string;

  @Field(() => Int, { nullable: true })
  @IsOptional()
  @IsInt()
  schoolId?: number;
}