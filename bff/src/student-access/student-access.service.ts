import { Injectable, Logger } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { StudentAccessDTO } from './dto/student-access.dto';
import { PaginationArgs } from '../common/pagination.args';
import { StudentAccessPage } from './dto/student-access-page.dto';

@Injectable()
export class StudentAccessService {
  private readonly studentAccessApiUrl: string;

  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService
  ) {
    this.studentAccessApiUrl = this.configService.get<string>('API_URL') ?? "http://localhost:8080/api";
  }

  async registerEntry(studentId: number): Promise<StudentAccessDTO> {
    const response = await firstValueFrom(
      this.httpService.post<StudentAccessDTO>(
        `${this.studentAccessApiUrl}/access/entry/${studentId}`,
        {}
      )
    );
    return response.data;
  }

  async registerExit(studentId: number): Promise<StudentAccessDTO> {
    const response = await firstValueFrom(
      this.httpService.post<StudentAccessDTO>(
        `${this.studentAccessApiUrl}/access/exit/${studentId}`,
        {}
      )
    );
    return response.data;
  }
  async getAllStudentAccess(paginationArgs: PaginationArgs): Promise<StudentAccessPage> {
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
    if (paginationArgs.sortDirection) {
      params.append('sortDirection', paginationArgs.sortDirection);
    }

    const response = await firstValueFrom(
      this.httpService.get<any>(`${this.studentAccessApiUrl}/access?${params.toString()}`)
    );

    return {
      content: response.data.content as StudentAccessDTO[],
      totalElements: response.data.totalElements,
      totalPages: response.data.totalPages,
      size: response.data.size,
      number: response.data.number,
    };
  }
}