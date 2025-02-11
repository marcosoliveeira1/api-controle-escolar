import { Test, TestingModule } from '@nestjs/testing';
import { SchoolResolver } from './school.resolver';
import { SchoolService } from './school.service';
import { SchoolDTO } from './dto/school.dto';
import { CreateSchoolInput } from './dto/create-school.input';
import { UpdateSchoolInput } from './dto/update-school.input';
import { PaginationArgs } from '../common/pagination.args';
import { SchoolPage } from './dto/school-page.dto';

describe('SchoolResolver', () => {
  let resolver: SchoolResolver;
  let service: SchoolService;

  const mockSchoolDTO: SchoolDTO = {
    id: 1,
    name: 'Test School',
    cnpj: '1234567890',
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

  const mockCreateSchoolInput: CreateSchoolInput = {
    name: 'Test School',
    cnpj: '1234567890',
    address: 'Test Address',
    phone: '123-456-7890',
    studentsPerClassroom: 20,
  };

  const mockUpdateSchoolInput: UpdateSchoolInput = {
    name: 'New School Name',
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        SchoolResolver,
        {
          provide: SchoolService,
          useValue: {
            createSchool: jest.fn().mockResolvedValue(mockSchoolDTO),
            getSchool: jest.fn().mockResolvedValue(mockSchoolDTO),
            updateSchool: jest.fn().mockResolvedValue(mockSchoolDTO),
            deleteSchool: jest.fn().mockResolvedValue(true),
            getAllSchools: jest.fn().mockResolvedValue(mockSchoolPage),
          },
        },
      ],
    }).compile();

    resolver = module.get<SchoolResolver>(SchoolResolver);
    service = module.get<SchoolService>(SchoolService);
  });

  it('should be defined', () => {
    expect(resolver).toBeDefined();
  });

  describe('createSchool', () => {
    it('should create a school', async () => {
      const result = await resolver.createSchool(mockCreateSchoolInput);
      expect(result).toEqual(mockSchoolDTO);
      expect(service.createSchool).toHaveBeenCalledWith(mockCreateSchoolInput);
    });
  });

  describe('getSchool', () => {
    it('should get a school by id', async () => {
      const result = await resolver.getSchool(1);
      expect(result).toEqual(mockSchoolDTO);
      expect(service.getSchool).toHaveBeenCalledWith(1);
    });
  });

  describe('updateSchool', () => {
    it('should update a school', async () => {
      const result = await resolver.updateSchool(1, mockUpdateSchoolInput);
      expect(result).toEqual(mockSchoolDTO);
      expect(service.updateSchool).toHaveBeenCalledWith(1, mockUpdateSchoolInput as SchoolDTO);
    });
  });

  describe('deleteSchool', () => {
    it('should delete a school', async () => {
      const result = await resolver.deleteSchool(1);
      expect(result).toEqual(true);
      expect(service.deleteSchool).toHaveBeenCalledWith(1);
    });
  });

  describe('getAllSchools', () => {
    it('should get all schools', async () => {
      const paginationArgs: PaginationArgs = { page: 0, size: 10 };
      const result = await resolver.getAllSchools(paginationArgs);
      expect(result).toEqual(mockSchoolPage);
      expect(service.getAllSchools).toHaveBeenCalledWith(paginationArgs);
    });
  });
});