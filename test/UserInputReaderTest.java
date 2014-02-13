import com.prateekj.ChatFactoryStub;
import com.prateekj.InputScanner;
import com.prateekj.UserInputReader;
import com.prateekj.UserInputReaderObserver;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class UserInputReaderTest {

    UserInputReaderObserver observer = mock(UserInputReaderObserver.class);
    InputScanner scanner = mock(InputScanner.class);
    ChatFactoryStub stub = new ChatFactoryStub();

    boolean firstCall = true;
    Answer<Boolean> return_true_only_firstTime = new Answer<Boolean>() {
        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            if(firstCall) {
                firstCall = false;
                return true;
            }
            return false;
        }
    };

    @Test
    public void readerInformsWhenUserQuits() throws InterruptedException {
        when(scanner.nextLine()).thenReturn("anything");
        when(scanner.hasNext()).then(return_true_only_firstTime);

        UserInputReader reader = new UserInputReader(stub,scanner, observer);
        reader.start();
        Thread.sleep(500);//To allow the UIR thread to start
        verify(observer, times(1)).onInput("anything");
    }
}
