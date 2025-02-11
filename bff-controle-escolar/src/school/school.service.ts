import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { SchoolDTO } from './dto/school.dto';
import { firstValueFrom } from 'rxjs';
import { ConfigService } from '@nestjs/config';
import { PaginationArgs } from 'src/common/pagination.args';
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

  async createSchool(schoolDTO: SchoolDTO): Promise<SchoolDTO> {
    try {
      const response = await firstValueFrom(
        this.httpService.post<SchoolDTO>(`${this.schoolApiUrl}/schools`, schoolDTO)
      );
      return response.data;
    } catch (error) {
      console.error('Error creating school:', error.response?.data || error.message);
      throw error;
    }
  }

  async getSchool(id: number): Promise<SchoolDTO> {
    try {
      const response = await firstValueFrom(
        this.httpService.get<SchoolDTO>(`${this.schoolApiUrl}/schools/${id}`)
      );
      return response.data;
    } catch (error) {
      console.error('Error getting school:', error.response?.data || error.message);
      throw error;
    }
  }

  async updateSchool(id: number, schoolDTO: SchoolDTO): Promise<SchoolDTO> {
    try {
      const response = await firstValueFrom(
        this.httpService.put<SchoolDTO>(`${this.schoolApiUrl}/schools/${id}`, schoolDTO)
      );
      return response.data;
    } catch (error) {
      console.error('Error updating school:', error.response?.data || error.message);
      throw error;
    }
  }

  async deleteSchool(id: number): Promise<boolean> {
    try {
      await firstValueFrom(
        this.httpService.delete<void>(`${this.schoolApiUrl}/schools/${id}`)
      );
      return true;
    } catch (error) {
      console.error('Error deleting school:', error.response?.data || error.message);
      throw error;
    }
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

    try {
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

    } catch (error) {
      console.error('Error getting all schools:', error.response?.data || error.message);
      throw error;
    }
  }


}