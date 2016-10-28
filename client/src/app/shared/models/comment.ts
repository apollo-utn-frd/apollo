export class Comment {
    constructor(
        public id: number,
        public uid: number,
        public rvid: number,
        public content: string,
        public dateCreated: Date,
        public lastUpdated: Date
    ) {}
}
