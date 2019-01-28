import * as React from 'react';
import GiphyImage from './GiphyImage';

interface Beer {
    id: number;
    name: string;
}

interface BeerListProps {
}

interface BeerListState {
    beers: Array<Beer>;
    isLoading: boolean;
}

// React.Component<P, S> - https://devblog.xero.com/typescript-and-react-whats-react-component-p-s-mean-cfddc65f81e1
class BeerList extends React.Component<BeerListProps
    , BeerListState> {
    constructor(props: BeerListProps) {
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
            <div>
                <h2>Beer List</h2>
                {beers.map((beer: Beer) =>
                    <div key={beer.id}>
                        {beer.name}<br/>
                        <GiphyImage name={beer.name}/>
                    </div>
                )}
            </div>

        );

    }
}

export default BeerList;