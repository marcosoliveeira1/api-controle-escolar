import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { StudentService } from './student.service';
import { StudentDTO } from './dto/student.dto';
import { CreateStudentInput } from './dto/create-student.input';
import { UpdateStudentInput } from './dto/update-student.input';
import { PaginationArgs } from '../common/pagination.args';
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
    return this.studentService.getStudent(id);
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
    await this.studentService.deleteStudent(id);
    return true;
  }
  @Query(() => StudentPage)
  async getAllStudents(
    @Args() paginationArgs: PaginationArgs
  ): Promise<StudentPage> {
    return this.studentService.getAllStudents(paginationArgs);
  }
}