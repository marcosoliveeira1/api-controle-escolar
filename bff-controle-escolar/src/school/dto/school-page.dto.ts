import { ObjectType, Field } from '@nestjs/graphql';
import { SchoolDTO } from './school.dto';
import { Page } from '../../common/page.dto';

@ObjectType()
export class SchoolPage extends Page {
  @Field(() => [SchoolDTO], { nullable: true })
  content?: SchoolDTO[];
}