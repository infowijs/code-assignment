import Conversation from "./Conversation";

const comments = [
  {
    id: 1,
    name: 'Leslie Alexander',
    imageUrl:
      'https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    content:
      'Explicabo nihil laborum. Saepe facilis consequuntur in eaque. Consequatur perspiciatis quam. Sed est illo quia. Culpa vitae placeat vitae. Repudiandae sunt exercitationem nihil nisi facilis placeat minima eveniet.',
    date: '1d ago',
    dateTime: '2023-03-04T15:54Z',
  },
  {
    id: 2,
    name: 'Michael Foster',
    imageUrl:
      'https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    content:
      'Laudantium quidem non et saepe vel sequi accusamus consequatur et. Saepe inventore veniam incidunt cumque et laborum nemo blanditiis rerum. A unde et molestiae autem ad. Architecto dolor ex accusantium maxime cumque laudantium itaque aut perferendis.',
    date: '2d ago',
    dateTime: '2023-03-03T14:02Z',
  },
  {
    id: 3,
    name: 'Dries Vincent',
    imageUrl:
      'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    content:
      'Quia animi harum in quis quidem sint. Ipsum dolorem molestias veritatis quis eveniet commodi assumenda temporibus. Dicta ut modi alias nisi. Veniam quia velit et ut. Id quas ducimus reprehenderit veniam fugit amet fugiat ipsum eius. Voluptas nobis earum in in vel corporis nisi.',
    date: '2d ago',
    dateTime: '2023-03-03T13:23Z',
    unread: true,
  },
  {
    id: 4,
    name: 'Lindsay Walton',
    imageUrl:
      'https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80',
    content:
      'Unde dolore exercitationem nobis reprehenderit rerum corporis accusamus. Nemo suscipit temporibus quidem dolorum. Nobis optio quae atque blanditiis aspernatur doloribus sit accusamus. Sunt reiciendis ut corrupti ab debitis dolorem dolorem nam sit. Ducimus nisi qui earum aliquam. Est nam doloribus culpa illum.',
    date: '3d ago',
    dateTime: '2023-03-02T21:13Z',
  },
]
export default function ConversationList() {
  return (
    <div className="hidden lg:block lg:w-96 bg-white py-5 flex-shrink-0 space-y-4 shadow z-10">
      <h2 className="text-lg px-8 font-bold pt-2">Berichten</h2>
      <ul className="divide-y divide-gray-100">
        {comments.map((comment) => (
          <Conversation
            content={comment.content}
            dateTime={comment.dateTime}
            date={comment.date}
            imageUrl={comment.imageUrl}
            name={comment.name}
            id={comment.id}
            unread={comment.unread || false}
            active={comment.id === 1}
            key={comment.id}
          />
        ))}
      </ul>
    </div>
  )
}
