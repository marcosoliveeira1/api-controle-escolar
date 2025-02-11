import { Test, TestingModule } from '@nestjs/testing';
import { StudentAccessService } from './student-access.service';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { of } from 'rxjs';
import { StudentAccessDTO } from './dto/student-access.dto';

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

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        StudentAccessService,
        {
          provide: HttpService,
          useValue: {
            post: jest.fn(),
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
});