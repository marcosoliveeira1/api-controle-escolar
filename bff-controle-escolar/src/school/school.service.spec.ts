import { Test, TestingModule } from '@nestjs/testing';
import { SchoolService } from './school.service';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { of } from 'rxjs';
import { SchoolDTO } from './dto/school.dto';
import { PaginationArgs } from '../common/pagination.args';
import { SchoolPage } from './dto/school-page.dto';

describe('SchoolService', () => {
  let schoolService: SchoolService;
  let httpService: HttpService;
  let configService: ConfigService;

  const mockSchoolDTO: SchoolDTO = {
    id: 1,
    name: 'Test School',
    cnpj: '12345678901234',
    address: 'Test Address',
    phone: '123-456-7890',
    studentsPerClassroom: 20,
  };

  const mockSchoolPage: SchoolPage = {
    content: [mockSchoolDTO],
    totalElements: 1,
    totalPages: 1,
    size: 10,
    number: 0,
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        SchoolService,
        {
          provide: HttpService,
          useValue: {
            post: jest.fn(),
            get: jest.fn(),
            put: jest.fn(),
            delete: jest.fn(),
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

    schoolService = module.get<SchoolService>(SchoolService);
    httpService = module.get<HttpService>(HttpService);
    configService = module.get<ConfigService>(ConfigService);
  });

  it('should be defined', () => {
    expect(schoolService).toBeDefined();
  });

  describe('createSchool', () => {
    it('should create a school', async () => {
      jest.spyOn(httpService, 'post').mockReturnValue(of({ data: mockSchoolDTO } as any));
      const result = await schoolService.createSchool(mockSchoolDTO);
      expect(result).toEqual(mockSchoolDTO);
      expect(httpService.post).toHaveBeenCalledWith('http://localhost:8080/api/schools', mockSchoolDTO);
    });
  });

  describe('getSchool', () => {
    it('should return a school by id', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockSchoolDTO } as any));
      const result = await schoolService.getSchool(1);
      expect(result).toEqual(mockSchoolDTO);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/schools/1');
    });
  });

  describe('updateSchool', () => {
    it('should update a school', async () => {
      jest.spyOn(httpService, 'put').mockReturnValue(of({ data: mockSchoolDTO } as any));
      const result = await schoolService.updateSchool(1, mockSchoolDTO);
      expect(result).toEqual(mockSchoolDTO);
      expect(httpService.put).toHaveBeenCalledWith('http://localhost:8080/api/schools/1', mockSchoolDTO);
    });
  });

  describe('deleteSchool', () => {
    it('should delete a school', async () => {
      jest.spyOn(httpService, 'delete').mockReturnValue(of({ data: {} } as any));
      const result = await schoolService.deleteSchool(1);
      expect(result).toEqual(true);
      expect(httpService.delete).toHaveBeenCalledWith('http://localhost:8080/api/schools/1');
    });
  });

  describe('getAllSchools', () => {
    it('should return all schools with pagination', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockSchoolPage } as any));
      const paginationArgs: PaginationArgs = { page: 0, size: 10 };
      const result = await schoolService.getAllSchools(paginationArgs);
      expect(result).toEqual(mockSchoolPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/schools?page=0&size=10');
    });

    it('should return all schools with default pagination if no args provided', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockSchoolPage } as any));
      const paginationArgs = {};
      const result = await schoolService.getAllSchools(paginationArgs as PaginationArgs);
      expect(result).toEqual(mockSchoolPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/schools?');
    });

    it('should return all schools with sorting', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockSchoolPage } as any));
      const paginationArgs: PaginationArgs = { sortBy: 'name' };
      const result = await schoolService.getAllSchools(paginationArgs);
      expect(result).toEqual(mockSchoolPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/schools?sortBy=name');
    });
  });
});