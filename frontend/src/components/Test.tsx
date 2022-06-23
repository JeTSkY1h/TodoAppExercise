import { useParams } from "react-router-dom"

export default function Test() {
    const {tagID} = useParams();
    return (
        <h1> {tagID}</h1>
    )
}