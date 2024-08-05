import classNames from "../modules/classNames";

export interface ConversationProps {
  id: number
  imageUrl: string
  name: string
  dateTime: string
  date: string
  content: string
  unread: boolean
  active: boolean
}

export default function Conversation({id, imageUrl, name, dateTime, date, content, unread = false, active = false}: ConversationProps) {
  return (
    <li key={id} className="group">
      <a href="#" className={classNames('group-hover:bg-gray-100 flex gap-x-4 px-8 py-6', active && 'bg-gray-50')}>
        <img alt="" src={imageUrl} className="h-12 w-12 flex-none rounded-full bg-gray-50" />
        <div className="flex-auto">
          <div className="flex items-baseline justify-between gap-x-4">
            <p className="text-sm font-semibold leading-6 text-gray-900">{name}</p>
            <p className="flex-none text-xs text-gray-600">
              <time dateTime={dateTime}>{date}</time>
            </p>
          </div>
          <div className="flex flex-row">
            <p className="mt-1 line-clamp-2 text-sm leading-6 text-gray-600">{content}</p>
            {unread && <div className="size-2.5 flex-shrink-0 self-end m-1.5 bg-green-500 rounded-full" />}
          </div>
        </div>
      </a>
    </li>
  )
}
