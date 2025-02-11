
import { Injectable, Inject } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { StudentDTO } from './dto/student.dto';
import { firstValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { PaginationArgs } from '../common/pagination.args';
import { StudentPage } from './dto/student-page.dto';

@Injectable()
export class StudentService {
  private readonly studentApiUrl: string;

  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService
  ) {
    this.studentApiUrl = this.configService.get<string>('API_URL') ?? "http://localhost:8080/api";
  }

  async createStudent(studentDTO: StudentDTO): Promise<StudentDTO> {
    const response = await firstValueFrom(
      this.httpService.post<StudentDTO>(`${this.studentApiUrl}/students`, studentDTO)
    );
    return response.data;
  }


  async getStudent(id: number): Promise<StudentDTO> {
    const response = await firstValueFrom(
      this.httpService.get<StudentDTO>(`${this.studentApiUrl}/students/${id}`)
    );
    return response.data;
  }

  async updateStudent(id: number, studentDTO: StudentDTO): Promise<StudentDTO> {
    const response = await firstValueFrom(
      this.httpService.put<StudentDTO>(`${this.studentApiUrl}/students/${id}`, studentDTO)
    );
    return response.data;
  }


  async deleteStudent(id: number): Promise<boolean> {
    await firstValueFrom(
      this.httpService.delete<void>(`${this.studentApiUrl}/students/${id}`)
    );
    return true;
  }

  async getAllStudents(paginationArgs: PaginationArgs): Promise<StudentPage> {
    const params = new URLSearchParams();
    if (paginationArgs.page !== undefined) {
      params.append('page', String(paginationArgs.page));
    }
    if (paginationArgs.size !== undefined) {
      params.append('size', String(paginationArgs.size));
    }
    if (paginationArgs.sortBy) {
      params.append('sortBy', paginationArgs.sortBy);
    }

    const response = await firstValueFrom(
      this.httpService.get<any>(`${this.studentApiUrl}/students?${params.toString()}`)
    );


    return {
      content: response.data.content as StudentDTO[],
      totalElements: response.data.totalElements,
      totalPages: response.data.totalPages,
      size: response.data.size,
      number: response.data.number,
    }
  }
}