import { Resolver, Mutation, Args, Query } from '@nestjs/graphql';
import { StudentAccessService } from './student-access.service';
import { StudentAccessDTO } from './dto/student-access.dto';
import { Int } from '@nestjs/graphql';
import { PaginationArgs } from '../common/pagination.args';
import { StudentAccessPage } from './dto/student-access-page.dto';

@Resolver()
export class StudentAccessResolver {
  constructor(private readonly studentAccessService: StudentAccessService) {}

  @Mutation(() => StudentAccessDTO)
  async registerEntry(
    @Args('studentId', { type: () => Int }) studentId: number,
  ): Promise<StudentAccessDTO> {
    return this.studentAccessService.registerEntry(studentId);
  }

  @Mutation(() => StudentAccessDTO)
  async registerExit(
    @Args('studentId', { type: () => Int }) studentId: number,
  ): Promise<StudentAccessDTO> {
    return this.studentAccessService.registerExit(studentId);
  }

  @Query(() => StudentAccessPage)
  async getAllStudentAccess(
    @Args() paginationArgs: PaginationArgs,
  ): Promise<StudentAccessPage> {
    return this.studentAccessService.getAllStudentAccess(paginationArgs);
  }

}