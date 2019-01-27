import * as React from 'react';
import './App.css';

import logo from './logo.svg';

interface Beer {
    id: number;
    name: string;
}

interface AppProps {
}

interface AppState {
    beers: Array<Beer>;
    isLoading: boolean;
}

// React.Component<P, S> - https://devblog.xero.com/typescript-and-react-whats-react-component-p-s-mean-cfddc65f81e1
class App extends React.Component<AppProps, AppState> {
    constructor(props: AppProps) {
        super(props);
        this.state = {
            beers: [],
            isLoading: false
        };
    }

    componentDidMount() {
        this.setState({isLoading: true});
        fetch('http://localhost:8080/good-beers')
            .then(response => response.json())
            .then(data => this.setState({beers: data, isLoading: false}));
    }

    render() {
        const {beers, isLoading} = this.state;
        if (isLoading) {
            return <p>Loading...</p>;
        }
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to React</h1>
                </header>
                <div>
                    <h2>Beer List</h2>
                    {beers.map((beer: Beer) =>
                        <div key={beer.id}>
                            {beer.name}
                        </div>
                    )}
                </div>
            </div>
        );

    }
}

export default App;
