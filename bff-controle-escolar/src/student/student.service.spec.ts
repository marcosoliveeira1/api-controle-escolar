import { Test, TestingModule } from '@nestjs/testing';
import { StudentService } from './student.service';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { of } from 'rxjs';
import { StudentDTO } from './dto/student.dto';
import { PaginationArgs } from '../common/pagination.args';
import { StudentPage } from './dto/student-page.dto';

describe('StudentService', () => {
  let studentService: StudentService;
  let httpService: HttpService;
  let configService: ConfigService;

  const mockStudentDTO: StudentDTO = {
    id: 1,
    firstName: 'John',
    lastName: 'Doe',
    gender: 'MALE',
    age: 10,
    level: 'ELEMENTARY',
    guardianName: 'Jane Doe',
    schoolId: 1,
  };

  const mockStudentPage: StudentPage = {
    content: [mockStudentDTO],
    totalElements: 1,
    totalPages: 1,
    size: 10,
    number: 0,
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        StudentService,
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

    studentService = module.get<StudentService>(StudentService);
    httpService = module.get<HttpService>(HttpService);
    configService = module.get<ConfigService>(ConfigService);
  });

  it('should be defined', () => {
    expect(studentService).toBeDefined();
  });

  describe('createStudent', () => {
    it('should create a student', async () => {
      jest.spyOn(httpService, 'post').mockReturnValue(of({ data: mockStudentDTO } as any));
      const result = await studentService.createStudent(mockStudentDTO);
      expect(result).toEqual(mockStudentDTO);
      expect(httpService.post).toHaveBeenCalledWith('http://localhost:8080/api/students', mockStudentDTO);
    });
  });

  describe('getStudent', () => {
    it('should return a student by id', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentDTO } as any));
      const result = await studentService.getStudent(1);
      expect(result).toEqual(mockStudentDTO);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/students/1');
    });
  });

  describe('updateStudent', () => {
    it('should update a student', async () => {
      jest.spyOn(httpService, 'put').mockReturnValue(of({ data: mockStudentDTO } as any));
      const result = await studentService.updateStudent(1, mockStudentDTO);
      expect(result).toEqual(mockStudentDTO);
      expect(httpService.put).toHaveBeenCalledWith('http://localhost:8080/api/students/1', mockStudentDTO);
    });
  });

  describe('deleteStudent', () => {
    it('should delete a student', async () => {
      jest.spyOn(httpService, 'delete').mockReturnValue(of({ data: {} } as any));
      const result = await studentService.deleteStudent(1);
      expect(result).toEqual(true);
      expect(httpService.delete).toHaveBeenCalledWith('http://localhost:8080/api/students/1');
    });
  });

  describe('getAllStudents', () => {
    it('should return all students with pagination', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentPage } as any));
      const paginationArgs: PaginationArgs = { page: 0, size: 10 };
      const result = await studentService.getAllStudents(paginationArgs);
      expect(result).toEqual(mockStudentPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/students?page=0&size=10');
    });

    it('should return all students with default pagination if no args provided', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentPage } as any));
      const paginationArgs = {};
      const result = await studentService.getAllStudents(paginationArgs as PaginationArgs);
      expect(result).toEqual(mockStudentPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/students?');
    });

    it('should return all students with sorting', async () => {
      jest.spyOn(httpService, 'get').mockReturnValue(of({ data: mockStudentPage } as any));
      const paginationArgs: PaginationArgs = { sortBy: 'firstName' };
      const result = await studentService.getAllStudents(paginationArgs);
      expect(result).toEqual(mockStudentPage);
      expect(httpService.get).toHaveBeenCalledWith('http://localhost:8080/api/students?sortBy=firstName');
    });
  });
});