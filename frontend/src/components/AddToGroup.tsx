import {UserCircleIcon} from "@heroicons/react/20/solid";
import {MessageProps} from "./Message";
import Date from "./Date";

export default function AddToGroup({person, date, added, me}: Partial<MessageProps>) {
  return (
    <>
      <div>
        <div className="relative px-1">
          <div className="flex h-8 w-8 items-center justify-center rounded-full bg-gray-100">
            <UserCircleIcon aria-hidden="true" className="h-5 w-5 text-gray-500" />
          </div>
        </div>
      </div>
      <div className="flex flex-row w-full">
        <div className="min-w-0 flex-1 py-1.5">
          <div className="text-sm text-gray-500">
            <a href={person?.href} className="font-medium text-gray-900">
              {person?.name}
            </a>{' '}
            added{' '}
            <a href={added?.href} className="font-medium text-gray-900">
              {added?.name}
            </a>{' '}
          </div>
        </div>
        <Date date={date} />
      </div>
    </>
  );
}
