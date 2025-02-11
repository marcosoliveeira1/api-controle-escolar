import { Module } from '@nestjs/common';
import { StudentAccessService } from './student-access.service';
import { StudentAccessResolver } from './student-access.resolver';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [HttpModule],
  providers: [StudentAccessService, StudentAccessResolver],
})
export class StudentAccessModule { }