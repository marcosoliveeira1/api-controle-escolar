import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { SchoolDTO } from './dto/school.dto';
import { firstValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { PaginationArgs } from '../common/pagination.args';
import { SchoolPage } from './dto/school-page.dto';

@Injectable()
export class SchoolService {
  private readonly schoolApiUrl: string;

  constructor(
    private readonly httpService: HttpService,
    private readonly configService: ConfigService
  ) {
    this.schoolApiUrl = this.configService.get<string>('API_URL') ?? "http://localhost:8080/api"
  }

  async createSchool(schoolDTO: any): Promise<any> {
    const response = await firstValueFrom(
      this.httpService.post<SchoolDTO>(`${this.schoolApiUrl}/schools`, schoolDTO)
    );
    return response.data;
  }

  async getSchool(id: number): Promise<SchoolDTO> {
    const response = await firstValueFrom(
      this.httpService.get<SchoolDTO>(`${this.schoolApiUrl}/schools/${id}`)
    );
    return response.data;
  }

  async updateSchool(id: number, updateSchoolInput: SchoolDTO): Promise<SchoolDTO> {
    const existingSchool = await this.getSchool(id);

    const allowedUpdates: string[] = [
      "name", "cnpj", "address", "phone", "studentsPerClassroom"
    ];

    const updates = Object.fromEntries(
      allowedUpdates.map(key => [key, updateSchoolInput[key]]).filter(([, value]) => value !== undefined)
    );

    Object.assign(existingSchool, updates);

    const response = await firstValueFrom(
      this.httpService.put<SchoolDTO>(`${this.schoolApiUrl}/schools/${id}`, existingSchool)
    );

    return response.data;
  }

  async deleteSchool(id: number): Promise<boolean> {
    await firstValueFrom(
      this.httpService.delete<void>(`${this.schoolApiUrl}/schools/${id}`)
    );
    return true;
  }

  async getAllSchools(paginationArgs: PaginationArgs): Promise<SchoolPage> {
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
      this.httpService.get<any>(`${this.schoolApiUrl}/schools?${params.toString()}`)
    );

    return {
      content: response.data.content as SchoolDTO[],
      totalElements: response.data.totalElements,
      totalPages: response.data.totalPages,
      size: response.data.size,
      number: response.data.number,
    };
  }
}