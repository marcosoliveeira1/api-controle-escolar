
import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { StudentService } from './student.service';
import { StudentDTO } from './dto/student.dto';
import { CreateStudentInput } from './dto/create-student.input';
import { UpdateStudentInput } from './dto/update-student.input';
import { Int } from '@nestjs/graphql';
import { PaginationArgs } from 'src/common/pagination.args';
import { StudentPage } from './dto/student-page.dto';

@Resolver()
export class StudentResolver {
  constructor(private readonly studentService: StudentService) { }


  @Mutation(() => StudentDTO)
  async createStudent(@Args('createStudentInput') createStudentInput: CreateStudentInput): Promise<StudentDTO> {
    return this.studentService.createStudent(createStudentInput);
  }


  @Query(() => StudentDTO, { nullable: true })
  async getStudent(@Args('id', { type: () => Number }) id: number): Promise<StudentDTO | null> {
    try {
      return this.studentService.getStudent(id);
    } catch (error) {
      console.error('Error in getStudent resolver:', error);
      throw error;
    }
  }


  @Mutation(() => StudentDTO)
  async updateStudent(
    @Args('id', { type: () => Number }) id: number,
    @Args('updateStudentInput') updateStudentInput: UpdateStudentInput
  ): Promise<StudentDTO> {
    return this.studentService.updateStudent(id, updateStudentInput as StudentDTO);
  }


  @Mutation(() => Boolean)
  async deleteStudent(@Args('id', { type: () => Number }) id: number): Promise<boolean> {
    try {
      await this.studentService.deleteStudent(id);
      return true;
    } catch (error) {
      console.error('Error in deleteStudent resolver:', error);
      throw error;
    }
  }
  @Query(() => StudentPage)
  async getAllStudents(
    @Args() paginationArgs: PaginationArgs
  ): Promise<StudentPage> {
    return this.studentService.getAllStudents(paginationArgs);
  }
}