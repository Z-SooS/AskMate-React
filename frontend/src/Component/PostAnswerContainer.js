import React from 'react';
import '../ComponentStyle/PostAnswerContainer.css';

function PostAnswerContainer({answerObject}) {
    return (
        <article data-answer={answerObject.id} className={"answer-container"}>
            <p className={"answer-message"}>{answerObject.message}</p>
            <div className={"answer-details"}>
                <span className={"answer-date"}>{answerObject.dateCreated}</span>
                <span className={"answer-score"}>{answerObject.score}</span>
                <div className={"answer-user-container"}>
                    <span className={"answer-user-reputation"}>{answerObject.user.reputation}</span>
                    <div className={"answer-user-display"}>
                        <span className={"answer-user-name"}>{answerObject.user.username}</span>
                         <span className={"answer-user-date"}>{answerObject.user.dateCreated}</span>
                    </div>
                </div>
            </div>
            {/*<aside className={"answer-buttons"}>*/}
            {/*todo- add buttons */}
            {/*</aside>*/}
        </article>
    );
}

export default PostAnswerContainer;