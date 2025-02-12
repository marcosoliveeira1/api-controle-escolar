import { Module } from '@nestjs/common';
import { registerEnumType } from '@nestjs/graphql';
import { Gender } from '../student/dto/enums/gender.enum';
import { StudentLevel } from '../student/dto/enums/level.enum';


@Module({
    providers: [],
})
export class CommonModule {}

registerEnumType(Gender, { name: 'Gender' });
registerEnumType(StudentLevel, { name: 'StudentLevel' });