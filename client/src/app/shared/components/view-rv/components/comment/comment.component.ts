import {Component, Input} from '@angular/core';
import {Comentario} from "../../../../models/comentario";

@Component({
  selector: 'apollo-comment',
  templateUrl: 'comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comentario;
}
