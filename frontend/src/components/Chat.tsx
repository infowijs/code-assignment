import Messages from "./Messages";
import Drawer from "./Drawer";
import {useState} from "react";
import {UserGroupIcon} from "@heroicons/react/24/outline";

export interface ChatProps {
  subject: string
}

export default function Chat({subject}: ChatProps) {
  const [open, setOpen] = useState(false)
  return (
    <div className="flex-1">
      <div className="flex flex-row items-center bg-white w-full px-8">
        <div className="pt-7 pb-5 font-bold flex-1">
          {subject}
        </div>
        <button
          type="button"
          onClick={() => setOpen(true)}
          className="rounded-full bg-green-600 p-1.5 text-white shadow-sm hover:bg-green-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-600"
        >
          <UserGroupIcon aria-hidden="true" className="size-5" />
        </button>
      </div>

      <div className="p-8">
        <Messages />
      </div>

      <Drawer open={open} setOpen={setOpen} />
    </div>
  );
}
