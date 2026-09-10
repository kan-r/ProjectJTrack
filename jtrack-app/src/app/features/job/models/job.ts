export interface Job {
  id: number;
  name: string;
  description: string;
  typeCode: string;
  typeDescription: string;
  priorityCode: string;
  priorityDescription: string;
  statusCode: string;
  statusDescription: string;
  assignedTo: string;
  assignedToName: string;
  estimatedHours: number;
  actualHours: number;
  sprintId: number;
  sprintName: string;
  parentId: number;
  parentName: string;
}
