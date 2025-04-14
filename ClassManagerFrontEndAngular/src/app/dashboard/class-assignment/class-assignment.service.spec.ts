import { TestBed } from '@angular/core/testing';

import { ClassAssignmentService } from './class-assignment.service';

describe('ClassAssignmentService', () => {
  let service: ClassAssignmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClassAssignmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
