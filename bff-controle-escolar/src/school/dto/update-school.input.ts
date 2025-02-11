import { InputType, Field, Int } from '@nestjs/graphql';
import { IsOptional, IsString, IsInt, Min } from 'class-validator';

@InputType()
export class UpdateSchoolInput {
  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  name?: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  cnpj?: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  address?: string;

  @Field({ nullable: true })
  @IsOptional()
  @IsString()
  phone?: string;

  @Field(() => Int, { nullable: true })
  @IsOptional()
  @IsInt()
  @Min(1)
  studentsPerClassroom?: number;
}