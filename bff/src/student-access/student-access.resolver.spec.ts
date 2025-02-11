import { Test, TestingModule } from '@nestjs/testing';
import { StudentAccessResolver } from './student-access.resolver';
import { StudentAccessService } from './student-access.service';
import { StudentAccessDTO } from './dto/student-access.dto';

describe('StudentAccessResolver', () => {
  let resolver: StudentAccessResolver;
  let service: StudentAccessService;

  const mockStudentAccessDTO: StudentAccessDTO = {
    studentId: 1,
    entryTime: '2024-01-01T08:00:00.000Z',
    exitTime: '2024-01-01T16:00:00.000Z',
    status: 'ACTIVE',
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        StudentAccessResolver,
        {
          provide: StudentAccessService,
          useValue: {
            registerEntry: jest.fn().mockResolvedValue(mockStudentAccessDTO),
            registerExit: jest.fn().mockResolvedValue(mockStudentAccessDTO),
          },
        },
      ],
    }).compile();

    resolver = module.get<StudentAccessResolver>(StudentAccessResolver);
    service = module.get<StudentAccessService>(StudentAccessService);
  });

  it('should be defined', () => {
    expect(resolver).toBeDefined();
  });

  describe('registerEntry', () => {
    it('should register entry', async () => {
      const result = await resolver.registerEntry(1);
      expect(result).toEqual(mockStudentAccessDTO);
      expect(service.registerEntry).toHaveBeenCalledWith(1);
    });
  });

  describe('registerExit', () => {
    it('should register exit', async () => {
      const result = await resolver.registerExit(1);
      expect(result).toEqual(mockStudentAccessDTO);
      expect(service.registerExit).toHaveBeenCalledWith(1);
    });
  });
});