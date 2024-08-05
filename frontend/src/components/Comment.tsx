import {MessageProps} from "./Message";
import Date from "./Date";
import {useFetchData} from "../modules/utilities";

export default function Comment({imageUrl, person, date, comment, me}: Partial<MessageProps>) {
  const u = useFetchData(person)

  if (u) {
    return null;
  }

  return (
    <>
      <div className="relative">
        <img
          alt=""
          src={imageUrl}
          className="flex h-10 w-10 items-center justify-center rounded-full bg-gray-400"
        />
      </div>
      <div className="flex flex-row w-full">
        <div className="min-w-0 flex-1">
          <div>
            <div className="text-sm">
              <a href={person?.href} className="font-medium text-gray-900">
                {person?.name}
              </a>
            </div>
          </div>
          <div className="mt-2 text-sm text-gray-700">
            <p>{comment}</p>
          </div>
        </div>
        <Date date={date} />
      </div>
    </>
  )
}
