import Message, {MessageProps, MessageType} from "./Message";

const activity: Partial<MessageProps>[] = [
  {
    id: 1,
    type: MessageType.comment,
    person: {name: 'Leslie Alexander', href: '#'},
    imageUrl:
      'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    comment:
      'Hey Thom, Heb je het al gehoord? Lindsay is onze nieuwe collega in ons team',
    date: '6d ago',
  },
  {
    id: 2,
    type: MessageType.comment,
    me: true,
    person: {name: 'Thom Cook', href: '#'},
    imageUrl:
      'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    comment:
      'Nee? Maar wat fijn dat jullie eindelijk iemand gevonden hebben! 🎉',
    date: '6d ago',
  },
  {
    id: 3,
    type: MessageType.addToGroup,
    person: {name: 'Leslie Alexander', href: '#'},
    added: {name: 'Lindsay Walton', href: '#'},
    date: '2d ago',
  },
  {
    id: 4,
    type: MessageType.comment,
    me: true,
    person: {name: 'Thom Cook', href: '#'},
    imageUrl:
      'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    comment:
      'Hey Lindsay! 👋 Welkom bij ons bedrijf! Wat leuk dat je bij ons komt werken! 💪',
    date: '1d ago',
  },
  {
    id: 5,
    type: MessageType.comment,
    person: {name: 'Lindsay Walton', href: '#'},
    imageUrl:
      'https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    comment:
      'Dank je wel  Thom! Ik ben afgelopen maandag begonnen, binnenkort even ☕️️ doen?',
    date: '2h ago',
  },
]

export default function Messages() {
  return (
    <div className="flow-root">
      <ul  className="-mb-8">
        {activity.map((activityItem, activityItemIdx) => (
          <li key={activityItem.id} className="group">
            <div className="relative pb-8">
              {activityItemIdx !== activity.length - 1 ? (
                <span aria-hidden="true" className="absolute left-5 top-5 -ml-px h-full w-0.5 bg-gray-200" />
              ) : null}
              <div className="relative flex items-start space-x-3">
                <Message
                  id={activityItem.id}
                  type={activityItem.type}
                  person={activityItem.person}
                  added={activityItem.added}
                  me={activityItem.me}
                  comment={activityItem.comment}
                  date={activityItem.date}
                  imageUrl={activityItem.imageUrl}
                  key={activityItem.id}
                />
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}
