import React from 'react';
import TopBar from "./components/TopBar";
import ConversationList from "./components/ConversationList";
import Chat from "./components/Chat";

function App() {
  return (
    <div className="bg-gray-100 h-screen">
      <TopBar />

      <div className="flex flex-content pt-16 h-screen w-screen">
        <ConversationList />
        <Chat subject="Gesprek met Leslie en Lindsay" />
      </div>
    </div>
  );
}

export default App;
