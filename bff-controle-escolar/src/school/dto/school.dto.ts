import { Field, Int, ObjectType } from '@nestjs/graphql';
import { IsNotEmpty, IsString, IsInt, Min, IsOptional } from 'class-validator';

@ObjectType()
export class SchoolDTO {
  @Field(() => Int, { nullable: true })
  id?: number;

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