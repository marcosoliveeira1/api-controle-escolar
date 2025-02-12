import { Test, TestingModule } from '@nestjs/testing';
import { StudentAccessService } from './student-access.service';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { of } from 'rxjs';
import { StudentAccessDTO } from './dto/student-access.dto';
import { PaginationArgs } from '../common/pagination.args';
import { StudentAccessPage } from './dto/student-access-page.dto';

describe('StudentAccessService', () => {
  let studentAccessService: StudentAccessService;
  let httpService: HttpService;
  let configService: ConfigService;

  const mockStudentAccessDTO: StudentAccessDTO = {
    studentId: 1,
    entryTime: '2024-01-01T08:00:00.000Z',
    exitTime: '2024-01-01T16:00:00.000Z',
    status: 'ACTIVE',
  };
  const mockStudentAccessPage: StudentAccessPage = {
    content: [mockStudentAccessDTO],
    totalElements: 1,
    totalPages: 1,
    size: 10,
    number: 0,
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        StudentAccessService,
        {
          provide: HttpService,
          useValue: {
            post: jest.fn(),
            get: jest.fn().mockReturnValue(of({ data: mockStudentAccessDTO } as any)),
          },
        },
        {
          provide: ConfigService,
          useValue: {
            get: jest.fn().mockReturnValue('http://localhost:8080/api'),
          },
        },
      ],
    }).compile();

    studentAccessService = module.get<StudentAccessService>(StudentAccessService);
    httpService = module.get<HttpService>(HttpService);
    configService = module.get<ConfigService>(ConfigService);
  });

  it('should be defined', () => {
    expect(studentAccessService).toBeDefined();
  });

  describe('registerEntry', () => {
    it('should register student entry', async () => {
      jest.spyOn(httpService, 'post').mockReturnValue(of({ data: mockStudentAccessDTO } as any));
      const result = await studentAccessService.registerEntry(1);
      expect(result).toEqual(mockStudentAccessDTO);
      expect(httpService.post).toHaveBeenCalledWith('http://localhost:8080/api/access/entry/1', {});
    });
  });

  describe('registerExit', () => {
    it('should register student exit', async () => {
      jest.spyOn(httpService, 'post').mockReturnValue(of({ data: mockStudentAccessDTO } as any));
      const result = await studentAccessService.registerExit(1);
      expect(result).toEqual(mockStudentAccessDTO);
      expect(httpService.post).toHaveBeenCalledWith('http://localhost:8080/api/access/exit/1', {});
    });
  });

  describe('getAllStudentAccess', () => {
    it('should return all student access records with pagination', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentAccessPage } as any));
      const paginationArgs: PaginationArgs = { page: 0, size: 10 };
      const result = await studentAccessService.getAllStudentAccess(paginationArgs);
      expect(result).toEqual(mockStudentAccessPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/access?page=0&size=10');
    });

    it('should return all student access records with default pagination if no args provided', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentAccessPage } as any));
      const paginationArgs = {};
      const result = await studentAccessService.getAllStudentAccess(paginationArgs as PaginationArgs);
      expect(result).toEqual(mockStudentAccessPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/access?');
    });

    it('should return all student access records with sorting', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentAccessPage } as any));
      const paginationArgs: PaginationArgs = { sortBy: 'entryTime' };
      const result = await studentAccessService.getAllStudentAccess(paginationArgs);
      expect(result).toEqual(mockStudentAccessPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/access?sortBy=entryTime');
    });
    it('should return all student access records with sorting and direction', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentAccessPage } as any));
      const paginationArgs: PaginationArgs = { sortBy: 'entryTime', sortDirection: 'DESC' };
      const result = await studentAccessService.getAllStudentAccess(paginationArgs);
      expect(result).toEqual(mockStudentAccessPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/access?sortBy=entryTime&sortDirection=DESC');
    });
  });
});