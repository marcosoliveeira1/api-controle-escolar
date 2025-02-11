import { ObjectType, Field, Int } from '@nestjs/graphql';
import { StudentDTO } from './student.dto';
import { Page } from '../../common/page.dto';

@ObjectType()
export class StudentPage extends Page {
  @Field(() => [StudentDTO], { nullable: true })
  content?: StudentDTO[];
}