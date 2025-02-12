import { ObjectType, Field } from '@nestjs/graphql';

import { Page } from '../../common/page.dto';
import { StudentAccessDTO } from './student-access.dto';

@ObjectType()
export class StudentAccessPage extends Page {
  @Field(() => [StudentAccessDTO], { nullable: true })
  content?: StudentAccessDTO[];
}