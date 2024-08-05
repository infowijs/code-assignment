import Comment from "./Comment";
import AddToGroup from "./AddToGroup";

export enum MessageType {
  comment = "comment",
  addToGroup = "add_to_group",
}

export interface MessageProps {
  id: number | undefined
  type: MessageType | undefined
  person: {
    name: string
    href: string
  } | undefined
  me: boolean | undefined
  date: string | undefined
  imageUrl: string | undefined
  comment: string | undefined
  added: {
    name: string
    href: string
  } | undefined
}

export default function Message({imageUrl, person, date, comment, added, me, type}: MessageProps) {
  switch (type) {
    case 'comment':
      return <Comment comment={comment} imageUrl={imageUrl} person={person} date={date} me={me} />
    case 'add_to_group':
      return <AddToGroup person={person} date={date} added={added} me={me} />
    default:
      return null
  }
}
