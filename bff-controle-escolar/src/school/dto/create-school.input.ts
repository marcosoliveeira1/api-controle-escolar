import { InputType, Field, Int } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsInt, Min } from 'class-validator';

@InputType()
export class CreateSchoolInput {
  @Field()
  @IsNotEmpty()
  @IsString()
  name: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  cnpj: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  address: string;

  @Field()
  @IsNotEmpty()
  @IsString()
  phone: string;

  @Field(() => Int)
  @IsInt()
  @Min(1)
  studentsPerClassroom: number;
}