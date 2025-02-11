import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { SchoolService } from './school.service';
import { SchoolDTO } from './dto/school.dto';
import { CreateSchoolInput } from './dto/create-school.input';
import { UpdateSchoolInput } from './dto/update-school.input';
import { Int } from '@nestjs/graphql';
import { PaginationArgs } from '../common/pagination.args';
import { SchoolPage } from './dto/school-page.dto';

@Resolver()
export class SchoolResolver {
  constructor(private readonly schoolService: SchoolService) { }

  @Mutation(() => SchoolDTO)
  async createSchool(@Args('createSchoolInput') createSchoolInput: CreateSchoolInput): Promise<SchoolDTO> {
    return this.schoolService.createSchool(createSchoolInput);
  }

  @Query(() => SchoolDTO, { nullable: true })
  async getSchool(@Args('id', { type: () => Int }) id: number): Promise<SchoolDTO | null> {
    return await this.schoolService.getSchool(id);
  }

  @Mutation(() => Boolean)
  async deleteSchool(@Args('id', { type: () => Int }) id: number): Promise<boolean> {
    await this.schoolService.deleteSchool(id);
    return true;
  }

  @Mutation(() => SchoolDTO)
  async updateSchool(
    @Args('id', { type: () => Int }) id: number,
    @Args('updateSchoolInput') updateSchoolInput: UpdateSchoolInput
  ): Promise<SchoolDTO> {
    return this.schoolService.updateSchool(id, updateSchoolInput as SchoolDTO);
  }

  @Query(() => SchoolPage)
  async getAllSchools(
    @Args() paginationArgs: PaginationArgs
  ): Promise<SchoolPage> {
    return this.schoolService.getAllSchools(paginationArgs);
  }
}