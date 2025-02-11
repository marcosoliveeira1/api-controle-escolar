import { Resolver, Mutation, Args } from '@nestjs/graphql';
import { StudentAccessService } from './student-access.service';
import { StudentAccessDTO } from './dto/student-access.dto';
import { Int } from '@nestjs/graphql';

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
}