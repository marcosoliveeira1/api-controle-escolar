import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { SchoolService } from './school.service';
import { SchoolDTO } from './dto/school.dto';
import { CreateSchoolInput } from './dto/create-school.input';
import { UpdateSchoolInput } from './dto/update-school.input';
import { Int } from '@nestjs/graphql';
import { PaginationArgs } from 'src/common/pagination.args';
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
    try {
      return this.schoolService.getSchool(id);
    } catch (error) {
      console.error('Error in getSchool resolver:', error);
      throw error;
    }
  }

  @Mutation(() => SchoolDTO)
  async updateSchool(
    @Args('id', { type: () => Int }) id: number,
    @Args('updateSchoolInput') updateSchoolInput: UpdateSchoolInput
  ): Promise<SchoolDTO> {
    return this.schoolService.updateSchool(id, updateSchoolInput as SchoolDTO);
  }

  @Mutation(() => Boolean)
  async deleteSchool(@Args('id', { type: () => Int }) id: number): Promise<boolean> {
    try {
      await this.schoolService.deleteSchool(id);
      return true;
    } catch (error) {
      console.error('Error in deleteSchool resolver:', error);
      throw error;
    }
  }

  @Query(() => SchoolPage)
  async getAllSchools(
    @Args() paginationArgs: PaginationArgs
  ): Promise<SchoolPage> {
    return this.schoolService.getAllSchools(paginationArgs);
  }
}