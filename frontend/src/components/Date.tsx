export interface DateProps {
    date: string | undefined
}

export default function Date ({date}: DateProps) {
  return <div className="mt-0.5 text-sm text-gray-400 opacity-0 group-hover:opacity-100 transition-opacity duration-500">{date}</div>
}
