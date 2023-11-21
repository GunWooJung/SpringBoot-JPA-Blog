const sliderContainer = document.getElementById('slider-container');
const sliderBarFilled = document.getElementById('slider-bar-filled');
const handleLeft = document.getElementById('handle-left');
const handleRight = document.getElementById('handle-right');
const valueLeft = document.getElementById('value-left');
const valueRight = document.getElementById('value-right');
const signRight = document.getElementById('sign-right')
const signLeft = document.getElementById('sign-left')

  const maxValue = 41; 
  const stepWidth = 280 / (maxValue - 1); 
  let leftValue = 0 * stepWidth;
  let rightValue = 40 * stepWidth;

  function setPosition(handle, newValue, isLeftHandle) {
    const position = newValue * stepWidth;
    handle.style.left = `${position}px`;
    if(isLeftHandle) {
      sliderBarFilled.style.left = `${position}px`;
      sliderBarFilled.style.width = `${rightValue - position}px`;
    } else {
      sliderBarFilled.style.width = `${position - leftValue}px`;
    }
  }

  function showSignLeft(handle, sign, value) { //좌sign위치
    sign.innerHTML = value; 
    sign.style.left = (handle.offsetLeft + handle.offsetWidth / 2) + 'px'; 
    sign.style.top = (handle.offsetTop + handle.offsetHeight + 5) + 'px'; 
  }

  function showSignRight(handle, sign, value) { //우sign위치
    sign.innerHTML = value; 
    sign.style.left = (handle.offsetLeft + handle.offsetWidth / 2) + 'px'; 
    sign.style.top = (handle.offsetTop - sign.offsetHeight - 10) + 'px'; 
  }

  function showSignInitial(){
    signLeft.innerHTML = (1).toFixed(1);
    signRight.innerHTML = (5).toFixed(1);
    signLeft.style.left = 10 + 'px'; 
    signRight.style.left = 290 + 'px'; 
    signLeft.style.top = 40 + 'px'; 
    signRight.style.top = -26 + 'px'; 
  }

  function updateSignValueLeft(handle, sign) {
    const value = 1 + leftValue / stepWidth / 10; // leftValue를 바탕으로 계산
    showSignLeft(handle, sign, value.toFixed(1)); 
  }
  function updateSignValueRight(handle, sign) {
  const value = 1.01 + rightValue / stepWidth / 10; // rightValue를 바탕으로 계산
  showSignRight(handle, sign, value.toFixed(1)); 
}
/*
  // numWindow 필요없을시 삭제
  function updateNumWindow(leftValue, rightValue) {
  const leftValDisplay = document.getElementById('leftValDisplay');
  const rightValDisplay = document.getElementById('rightValDisplay');
  leftValDisplay.textContent = Math.round(leftValue / stepWidth + 1)/10;
  rightValDisplay.textContent = Math.round(rightValue / stepWidth + 1)/10;
}
  //numWindow 필요없을시 삭제
*/
function updateHandlePosition(handle, newX, isLeftHandle) {
  let newStepPosition;
  newStepPosition = newX;

  if (isLeftHandle) {
    // leftHandle이 rightHandle 위치까지 이동할 수 있도록 제한 제거
    newStepPosition = Math.max(newStepPosition, 0);
    newStepPosition = Math.min(newStepPosition, rightValue); // 겹칠 수 있음
  } else {
    // rightHandle이 leftHandle 위치까지 이동할 수 있도록 제한 제거
    newStepPosition = Math.max(newStepPosition, leftValue); // 겹칠 수 있음
    newStepPosition = Math.min(newStepPosition, sliderContainer.offsetWidth - handle.offsetWidth);
  }

  handle.style.left = `${newStepPosition}px`; 
  return newStepPosition;
}

function handleTouch(e) {
  e.preventDefault();
  const handle = e.target;
  const rect = sliderBarFilled.getBoundingClientRect();
  const isLeftHandle = handle === handleLeft;


  document.ontouchmove = (event) => {
    event.preventDefault();
    let newX = event.touches[0].clientX - rect.left;
    if (isLeftHandle) {
      newX = Math.min(newX, rightValue - stepWidth);
      if (newX <= 0){
        newX = 0.1;
      }
      leftValue = updateHandlePosition(handleLeft, newX, isLeftHandle);
    } else {
      newX = Math.max(newX, leftValue + stepWidth);
      rightValue = updateHandlePosition(handleRight, newX, isLeftHandle);
    }

    updateSignValueLeft(handleLeft, signLeft, true);
    updateSignValueRight(handleRight, signRight, false);
  };

  document.ontouchstart = (event) => {
    event.preventDefault();
  }
  document.ontouchend = () => {
    document.ontouchmove = null;
    document.ontouchend = null;
  };
}

function handleDrag(e) {

  const handle = e.target;
  const rect = sliderBarFilled.getBoundingClientRect();
  const isLeftHandle = handle === handleLeft;

  document.onmousemove = (event) => {

    let newX = event.clientX - rect.left;
    if (isLeftHandle) {
      
      newX = Math.min(newX, rightValue - stepWidth);
      if (newX <= 0){
        newX = 0.1;
      }
      leftValue = updateHandlePosition(handleLeft, newX, isLeftHandle);
    } else {
      newX = Math.max(newX, leftValue + stepWidth);
      rightValue = updateHandlePosition(handleRight, newX, isLeftHandle);
    }
    // 업데이트된 leftValue와 rightValue를 사용하여 사인 업데이트
    updateSignValueLeft(handleLeft, signLeft, true);
    updateSignValueRight(handleRight, signRight, false);

  };

  document.onmouseup = () => {
    document.onmousemove = null;
    document.onmouseup = null;
  };
}
  showSignInitial();
  // numWindow 필요없을시 삭제
  //updateNumWindow(leftValue, rightValue);
  // numWindow 필요없을시 삭제
  handleLeft.onmousedown = handleDrag;
  handleRight.onmousedown = handleDrag;
  handleLeft.ontouchstart = handleTouch;
  handleRight.ontouchstart = handleTouch;