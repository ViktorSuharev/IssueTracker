import React from "react";
import HeaderPanel from "./header/HeaderPanel";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      total: null,
      next: null,
      operation: null,
    };
  }

  render() {
    return (
      <div>
          <HeaderPanel/>
          <h1>There will be Tracker Issue application soon...</h1>
      </div>
    );
  }
}
export default App;
