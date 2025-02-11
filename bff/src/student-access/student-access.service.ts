import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { StudentAccessDTO } from './dto/student-access.dto';

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
}