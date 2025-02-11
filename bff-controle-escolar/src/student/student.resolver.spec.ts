import { Test, TestingModule } from '@nestjs/testing';
import { StudentResolver } from './student.resolver';
import { StudentService } from './student.service';
import { StudentDTO } from './dto/student.dto';
import { CreateStudentInput } from './dto/create-student.input';
import { UpdateStudentInput } from './dto/update-student.input';
import { Gender } from './dto/enums/gender.enum';
import { StudentLevel } from './dto/enums/level.enum';
import { PaginationArgs } from '../common/pagination.args';
import { StudentPage } from './dto/student-page.dto';

describe('StudentResolver', () => {
  let resolver: StudentResolver;
  let service: StudentService;

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

  const mockCreateStudentInput: CreateStudentInput = {
    firstName: 'John',
    lastName: 'Doe',
    gender: Gender.MALE,
    age: 10,
    level: StudentLevel.ELEMENTARY,
    guardianName: 'Jane Doe',
    schoolId: 1,
  };

  const mockUpdateStudentInput: UpdateStudentInput = {
    firstName: 'Jane',
  };


  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        StudentResolver,
        {
          provide: StudentService,
          useValue: {
            createStudent: jest.fn().mockResolvedValue(mockStudentDTO),
            getStudent: jest.fn().mockResolvedValue(mockStudentDTO),
            updateStudent: jest.fn().mockResolvedValue(mockStudentDTO),
            deleteStudent: jest.fn().mockResolvedValue(true),
            getAllStudents: jest.fn().mockResolvedValue(mockStudentPage),
          },
        },
      ],
    }).compile();

    resolver = module.get<StudentResolver>(StudentResolver);
    service = module.get<StudentService>(StudentService);
  });

  it('should be defined', () => {
    expect(resolver).toBeDefined();
  });

  describe('createStudent', () => {
    it('should create a student', async () => {
      const result = await resolver.createStudent(mockCreateStudentInput);
      expect(result).toEqual(mockStudentDTO);
      expect(service.createStudent).toHaveBeenCalledWith(mockCreateStudentInput);
    });
  });

  describe('getStudent', () => {
    it('should get a student by id', async () => {
      const result = await resolver.getStudent(1);
      expect(result).toEqual(mockStudentDTO);
      expect(service.getStudent).toHaveBeenCalledWith(1);
    });
  });

  describe('updateStudent', () => {
    it('should update a student', async () => {
      const result = await resolver.updateStudent(1, mockUpdateStudentInput);
      expect(result).toEqual(mockStudentDTO);
      expect(service.updateStudent).toHaveBeenCalledWith(1, mockUpdateStudentInput as StudentDTO);
    });
  });

  describe('deleteStudent', () => {
    it('should delete a student', async () => {
      const result = await resolver.deleteStudent(1);
      expect(result).toEqual(true);
      expect(service.deleteStudent).toHaveBeenCalledWith(1);
    });
  });

  describe('getAllStudents', () => {
    it('should get all students', async () => {
      const paginationArgs: PaginationArgs = { page: 0, size: 10 };
      const result = await resolver.getAllStudents(paginationArgs);
      expect(result).toEqual(mockStudentPage);
      expect(service.getAllStudents).toHaveBeenCalledWith(paginationArgs);
    });
  });
});